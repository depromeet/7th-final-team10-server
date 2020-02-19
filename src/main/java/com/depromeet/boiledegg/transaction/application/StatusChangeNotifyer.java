package com.depromeet.boiledegg.transaction.application;

import com.depromeet.boiledegg.transaction.domain.event.StatusChangeEvent;

@FunctionalInterface
public interface StatusChangeNotifyer {

    void notify(final StatusChangeEvent event);
}
