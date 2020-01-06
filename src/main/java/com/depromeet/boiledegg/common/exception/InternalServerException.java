package com.depromeet.boiledegg.common.exception;

public final class InternalServerException extends BaseRuntimeException {

    @Override
    public ErrorStatus getErrorStatus() {
        return ErrorCode.INTERNAL_SERVER_ERROR;
    }
}
