package com.depromeet.boiledegg.common.exception;

public final class ForbiddenException extends BaseRuntimeException {

    @Override
    public ErrorStatus getErrorStatus() {
        return ErrorCode.FORBIDDEN;
    }
}
