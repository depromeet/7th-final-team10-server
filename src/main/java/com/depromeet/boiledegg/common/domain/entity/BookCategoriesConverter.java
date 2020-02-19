package com.depromeet.boiledegg.common.domain.entity;

import com.depromeet.boiledegg.book.domain.BookCategory;
import com.depromeet.boiledegg.common.utils.Collections;
import com.depromeet.boiledegg.common.utils.Strings;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.Arrays;
import java.util.Set;

import static com.depromeet.boiledegg.common.utils.Collections.emptySet;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toUnmodifiableSet;

@Converter
public final class BookCategoriesConverter implements AttributeConverter<Set<BookCategory>, String> {

    private static final String DELIMITER = ",";

    @Override
    public String convertToDatabaseColumn(final Set<BookCategory> attributes) {
        if (Collections.isEmpty(attributes)) {
            return null;
        }

        return attributes.stream()
                .map(Enum::name)
                .collect(joining(DELIMITER));
    }

    @Override
    public Set<BookCategory> convertToEntityAttribute(final String dbData) {
        if (Strings.isBlank(dbData)) {
            return emptySet();
        }

        return Arrays.stream(dbData.split(DELIMITER))
                .map(BookCategory::valueOf)
                .collect(toUnmodifiableSet());
    }
}
