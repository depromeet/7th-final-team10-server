package com.depromeet.boiledegg.book.domain;

public enum BookCategory {

    NOVEL,
    POETRY_AND_ESSAY,
    ECONOMIC_MANAGEMENT,
    SELF_DEVELOPMENT,
    HUMANITIES,
    HISTORY_AND_CULTURE,
    POLITICS_AND_SOCIETY,
    HEALTH_AND_MEDICINE,
    NATIONAL_LANGUAGE_AND_FOREIGN_LANGUAGE,
    TEXTBOOK_AND_EXAMINATION_BOOK,
    IT_AND_PROGRAMMING,
    SCIENCE_AND_ENGINEERING,
    ART_AND_POPULAR_CULTURE,
    RELIGION,
    HOME_AND_LIFE_AND_COOKING,
    TRIP_AND_HOBBY,
    MAGAZINE,
    YOUTH_CULTURE,
    CHILD,
    INFANT_CHILD,
    E_AUDIO_BOOK,
    BOOK_MORNING_CEO,
    FOREIGN,
    ;

    public static int size() {
        return values().length;
    }
}
