package com.pleyfair.mipmip.model.dto.endato;

import jakarta.persistence.*;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Date;

@Entity
@Builder(toBuilder = true)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Getter
@Setter
@Slf4j
@Table(name="EmailEngagementData")
public class EmailEngagementData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @OneToOne
    @JoinColumn(name = "email_address_id")
    EmailAddress emailAddress;

    @Column(name = "last_check_date")
    public Date lastCheckDate;

    @Column(name = "is_good_domain")
    public boolean isGoodDomain;

    @Column(name = "is_matched")
    public boolean isMatched;

    @Column(name = "engagement_score")
    public int engagementScore;

    @Column(name = "last_touch_date")
    public Date lastTouchDate;

    @Column(name = "send_time")
    public int sendTime;

    @Column(name = "best_day_of_the_week")
    public String bestDayOfTheWeek;

    @Column(name = "best_time_of_the_day")
    public String bestTimeOfTheDay;

    @Column(name = "frequency")
    public String frequency;

    @Column(name = "naics")
    public ArrayList<String> naics;

    @Column(name = "is_bounce")
    public boolean isBounce;
}