package com.depromeet.boiledegg.notification.exception;

import com.depromeet.boiledegg.common.exception.BaseRuntimeException;
import com.depromeet.boiledegg.common.exception.ErrorCode;
import com.depromeet.boiledegg.common.exception.ErrorStatus;

public final class NotificationNotFoundException extends BaseRuntimeException {

    @Override
    public ErrorStatus getErrorStatus() {
        return ErrorCode.NOTIFICATION_NOT_FOUND;
    }
}
