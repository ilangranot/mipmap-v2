package com.pleyfair.mipmip.helper;

import com.pleyfair.mipmip.model.dto.endato.Response;
import com.pleyfair.mipmip.model.dto.endato.*;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

public class LinkingAndLearningHelper {
    /**
     * Processes the response and all related entities to be bi-directionally linked
     * @param response the response from Endato
     */
    public static void processBiDirectionalLinkingAndLearningForResponse(Response response) {
        try {
            // set response
            response.getCounts().setResponse(response);
            response.getSmartSearchStatistics().setResponse(response);
            response.getPagination().setResponse(response);
            response.getError().setResponse(response);
            // process person and learning
            processPersons(response);
        } catch (Exception exception) {
            System.out.println("unable to process " + response.getId());
        }
    }

    private static void processPersons(Response response) {
        List<Person> personList = response.getPersons();
        personList.forEach(
                (person -> {
                    // set response
                    person.setResponse(response);
                    // process One-to-one
                    person.getName().setPerson(person);
                    person.getPropensityToPayScore().setPerson(person);
                    person.getIndicators().setPerson(person);
                    // process one-to-many
                    processAddresses(person);
                    processPhoneNumbers(person);
                    processEmailAddresses(person);
                    processAkas(person);
                    processAssociatesSummary(person);
                    processDatesOfBirth(person);
                    processDatesOfDeath(person);
                    processLocations(person);
                    processRealtivesSummary(person);
                    processMergedNames(person);
                    // process associates (one-to-many)
                    if (person.getAssociates() != null)
                        processAssociates(person);
                    // process learning
                    processLearning(person);
                    // check if person is the main person to search
                    boolean isPersonMain = isPersonMain(person, response);
                    if (isPersonMain) {
                        person.setMain(true);
                        response.setMainFound(true);
                    }
                    // check if this person is associated with others and mark how many
                    if (personList.size() > 1)
                        person.setHasRelated(true);
                    person.setTotalRelated(personList.size() - 1);
                })
        );
    }

    private static boolean isPersonMain(Person person, Response response) {
        if (person.getName().getFirstName().toLowerCase().contains(
                response.getRequest().getFirstName().toLowerCase())
                || response.getRequest().getFirstName().toLowerCase().contains(
                        person.getName().getFirstName().toLowerCase())
        )
            return true;
        else {
            int commonFirstNameCharCount =  commonCharacterCount(person.getName().getFirstName().toLowerCase(),response.getRequest().getFirstName().toLowerCase());
            int commonLastNameCharCount = commonCharacterCount(person.getName().getLastName().toLowerCase(),response.getRequest().getLastName().toLowerCase());
            person.setCommonNameCharsCount(commonFirstNameCharCount + commonLastNameCharCount);
            return false;
        }
    }

    private static int commonCharacterCount(String s1, String s2) {
        Map<Character, Integer> s1CharacterCount = getCharacterCount(s1);
        Map<Character, Integer> s2CharacterCount = getCharacterCount(s2);
        return s1CharacterCount.keySet().stream()
                .filter(s2CharacterCount.keySet()::contains)
                .mapToInt(c -> Math.min(s1CharacterCount.get(c), s2CharacterCount.get(c)))
                .sum();
    }

    private static Map<Character, Integer> getCharacterCount(String s) {
        Map<Character, Integer> characterCount = new HashMap<>();
        for (char c: s.toCharArray()) {
            characterCount.put(c, characterCount.computeIfAbsent(c, count -> 0) + 1);
        }
        return characterCount;
    }

    private static void processLearning(Person person) {
        // address learning
        List<Address> addressList = person.getAddresses();
        StringBuilder adressesStringBuilder = new StringBuilder();
        AtomicReference<LocalDate> firstSeenLocalDate = new AtomicReference<>(LocalDate.now());
        AtomicReference<LocalDate> lastSeenLocalDate = new AtomicReference<>(getParsedLocalDateOf("01/01/1970"));
        processAddressLearning(addressList, adressesStringBuilder, firstSeenLocalDate, lastSeenLocalDate);
        int addressesCount = addressList.size();
        float numberOfYearsBetweenFirstAndLastDates =
                Duration.between(
                        firstSeenLocalDate.get().atStartOfDay(),lastSeenLocalDate.get().atStartOfDay()
                ).toDays() / 365;
        float stability = numberOfYearsBetweenFirstAndLastDates / addressesCount;
        // indicators learning
        boolean hasNegativeIndicators = false;
        String indicatorsString = getIndicatorsString(person);
        if (!indicatorsString.contains("No negative records found"))
            hasNegativeIndicators = true;
        // persist learning
        Learning learning = Learning.builder()
                .person(person)
                .age(person.age)
                .addressesPrettyPrint(adressesStringBuilder.toString())
                .firstSeenLocalDate(firstSeenLocalDate.toString())
                .lastSeenLocalDate(lastSeenLocalDate.toString())
                .addressesCount(addressesCount)
                .stability(stability)
                .hasNegativeIndicators(hasNegativeIndicators)
                .indicatorsPrettyPrint(indicatorsString)
                .build();
        person.setLearning(learning);
    }

    private static void processAddressLearning(List<Address> addressList, StringBuilder adressesStringBuilder, AtomicReference<LocalDate> firstSeenLocalDate, AtomicReference<LocalDate> lastSeenLocalDate) {
        addressList.forEach(address -> {
                    // find minimum date for all addresses
                    if ( getParsedLocalDateOf(address.getFirstReportedDate()).isBefore(firstSeenLocalDate.get()) )
                        firstSeenLocalDate.set(getParsedLocalDateOf(address.getFirstReportedDate()));

                    if ( getParsedLocalDateOf(address.getLastReportedDate()).isAfter(lastSeenLocalDate.get()) )
                        lastSeenLocalDate.set(getParsedLocalDateOf(address.getLastReportedDate()));

                    adressesStringBuilder.append(
                            address.getFirstReportedDate() + " - " +
                                    address.getLastReportedDate() + " "
                                    + address.getFullAddress() + "\n"
                    );
                }
        );
    }

    private static String getIndicatorsString(Person person) {
        StringBuilder indicatorsStringBuilder = new StringBuilder();
        Indicators indicators = person.getIndicators();
        if (indicators.getHasBankruptcyRecords() > 0 )
            indicatorsStringBuilder.append("Has Bankruptcy Records: " + indicators.getHasBankruptcyRecords() + "\n");

        if (indicators.getHasDeaRecords() > 0 )
            indicatorsStringBuilder.append("Has DEA Records: " + indicators.getHasDeaRecords() + "\n");

        if (indicators.getHasDebtRecords() > 0 )
            indicatorsStringBuilder.append("Has Debt Records: " + indicators.getHasDebtRecords() + "\n");

        if (indicators.getHasEvictionsRecords() > 0)
            indicatorsStringBuilder.append("Has Eviction Records: " + indicators.getHasEvictionsRecords() + "\n");

        if (indicators.getHasForeclosuresRecords() > 0 || indicators.getHasForeclosuresV2Records() > 0)
            indicatorsStringBuilder.append("Has Foreclosure Records: " +
                    indicators.getHasForeclosuresRecords() + indicators.getHasForeclosuresV2Records() + "\n");

        if (indicators.getHasUccRecords() > 0)
            indicatorsStringBuilder.append("Has UCC Records: " + indicators.getHasUccRecords() + "\n");

        if (indicators.getHasUnbankedData() > 0)
            indicatorsStringBuilder.append("Has Unbanked Data: " + indicators.getHasUnbankedData() + "\n");

        if (indicators.getHasLienRecords() > 0)
            indicatorsStringBuilder.append("Has Lien Records: " + indicators.getHasLienRecords() + "\n");

        if (indicators.getHasJudgmentRecords() > 0)
            indicatorsStringBuilder.append("Has Judgement Records: " + indicators.getHasJudgmentRecords() + "\n");

        if (indicatorsStringBuilder.isEmpty())
            indicatorsStringBuilder.append("No negative records found.\n");

        if (indicators.getHasAddresses() > 0)
            indicatorsStringBuilder.append("Has Addresses: " + indicators.getHasAddresses() + "\n");

        if (indicators.getHasCurrentAddresses() > 0)
            indicatorsStringBuilder.append("Has Current Address Records: " + indicators.getHasCurrentAddresses() + "\n");

        if (indicators.getHasFeinRecords() > 0)
            indicatorsStringBuilder.append("Has EIN Records: " + indicators.getHasFeinRecords() + "\n");

        if (indicators.getHasBusinessRecords() > 0)
            indicatorsStringBuilder.append("Has Business Records: " + indicators.getHasBusinessRecords() + "\n");

        if (indicators.getHasDivorceRecords() > 0)
            indicatorsStringBuilder.append("Has Divorce Records: " + indicators.getHasDivorceRecords() + "\n");

        if (indicators.getHasMarriageRecords() > 0)
            indicatorsStringBuilder.append("Has Marriage Records: " + indicators.getHasMarriageRecords() + "\n");

        int commRecords = indicators.getHasDomainsRecords() + indicators.getHasEmails() + indicators.getHasLandLines()
                + indicators.getHasMobilePhones();
        if (commRecords > 0)
            indicatorsStringBuilder.append("Has Communication Records: " + commRecords + "\n");

        if (indicators.getHasVehicleRegistrationsRecords() > 0)
            indicatorsStringBuilder.append("Has Vehicle Records: " + indicators.getHasVehicleRegistrationsRecords() + "\n");

        if (indicators.getHasProfessionalLicenseRecords() > 0)
            indicatorsStringBuilder.append("Has Professional License Records: " + indicators.getHasProfessionalLicenseRecords() + "\n");

        if (indicators.getHasWorkplaceRecords() > 0)
            indicatorsStringBuilder.append("Has Workplace Records: " + indicators.getHasWorkplaceRecords() + "\n");

        if (indicators.getHasPropertyRecords() > 0 || indicators.getHasPropertyV2Records() > 0)
            indicatorsStringBuilder.append("Has Property Records: " + indicators.getHasPropertyV2Records() + indicators.getHasPropertyRecords() + "\n");

        if (indicatorsStringBuilder.length() < "No negative records were found.\n".length() + 1)
            indicatorsStringBuilder.append("No other records were found.");
        return indicatorsStringBuilder.toString();
    }

    private static LocalDate getParsedLocalDateOf(String dateString) {
        return LocalDate.parse(dateString, DateTimeFormatter.ofPattern("M/d/u")); //format 3/1/2024
    }

    private static void processAssociates(Person person) {
        if (person.getAssociates() != null) {
            person.getAssociates().forEach(associate -> {
                associate.setPerson(person);
                associate.getName().setAssociate(associate);
                associate.getDatesOfBirth().forEach(datesOfBirth -> datesOfBirth.setAssociate(associate));
                associate.getDatesOfDeath().forEach(datesOfDeath -> datesOfDeath.setAssociate(associate));
                associate.getPhoneNumbers().forEach(phoneNumber -> phoneNumber.setAssociate(associate));
                associate.getAddresses().forEach(address -> {
                    address.setAssociate(associate);
                    address.getHighRiskMarker().setAddress(address);
                });
                associate.getEmailAddresses().forEach(emailAddress -> {
                    emailAddress.setAssociate(associate);
                    emailAddress.getEmailEngagementData().setEmailAddress(emailAddress);
                });
                associate.getDeathRecords().setAssociate(associate);
                associate.getAkas().forEach(aka -> aka.setAssociate(associate));
                associate.getMergedNames().forEach(mergedName -> mergedName.setAssociate(associate));
                associate.getLocations().forEach(location -> location.setAssociate(associate));
                associate.getPropensityToPayScore().setAssociate(associate);
            });
        }
    }

    private static void processMergedNames(Person person) {
        if (person.getMergedNames() != null)
            person.getMergedNames().forEach(mergedName -> mergedName.setPerson(person));
    }

    private static void processRealtivesSummary(Person person) {
        if (person.getRelativesSummary() != null)
            person.getRelativesSummary().forEach(relativesSummary -> relativesSummary.setPerson(person));
    }

    private static void processLocations(Person person) {
        if (person.getLocations() != null)
            person.getLocations().forEach(location -> location.setPerson(person));
    }

    private static void processDatesOfBirth(Person person) {
        if (person.getDatesOfBirth() != null)
            person.getDatesOfBirth().forEach(datesOfBirth -> datesOfBirth.setPerson(person));
    }

    private static void processDatesOfDeath(Person person) {
        if (person.getDatesOfDeath() != null)
            person.getDatesOfDeath().forEach(datesOfDeath -> datesOfDeath.setPerson(person));
    }

    private static void processAssociatesSummary(Person person) {
        if (person.getAssociatesSummary() != null)
            person.getAssociatesSummary().forEach(associatesSummary -> associatesSummary.setPerson(person));
    }

    private static void processAkas(Person person) {
        if (person.getAkas() != null)
            person.getAkas().forEach(aka -> aka.setPerson(person));
    }

    private static void processEmailAddresses(Person person) {
        if (person.getEmailAddresses() != null)
            person.getEmailAddresses().forEach(emailAddress -> {emailAddress.setPerson(person);});
    }

    private static void processPhoneNumbers(Person person) {
        if (person.getPhoneNumbers() != null)
            person.getPhoneNumbers().forEach(phoneNumber -> phoneNumber.setPerson(person));
    }

    private static void processAddresses(Person person) {
        if (person.getAddresses() != null)
            person.getAddresses().forEach(address -> {
                address.setPerson(person);
                address.getHighRiskMarker().setAddress(address);
            });
    }
}