package com.depromeet.boiledegg.common.infrastructure.book;

import java.util.Date;
import java.util.List;

public interface BookInformation {

    String getTitle();

    String getContents();

    List<String> getAuthors();

    List<String> getTranslators();

    String getIsbn();

    int getPrice();

    String getPublisher();

    String getThumbnail();

    Date getPublishedAt();
}
