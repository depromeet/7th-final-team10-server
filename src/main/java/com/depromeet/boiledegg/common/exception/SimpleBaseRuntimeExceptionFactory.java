package com.depromeet.boiledegg.common.exception;

import org.springframework.stereotype.Component;

@Component
final class SimpleBaseRuntimeExceptionFactory implements BaseRuntimeExceptionFactory {

    @Override
    public BaseRuntimeException create(final ErrorStatus errorStatus) {
        return SimpleBaseException.of(errorStatus);
    }

    private static class SimpleBaseException extends BaseRuntimeException {

        private final ErrorStatus errorStatus;

        private SimpleBaseException(final ErrorStatus errorStatus) {
            super(errorStatus.getMessage());
            this.errorStatus = errorStatus;
        }

        private static BaseRuntimeException of(final ErrorStatus errorStatus) {
            return new SimpleBaseException(errorStatus);
        }

        @Override
        public ErrorStatus getErrorStatus() {
            return errorStatus;
        }
    }
}
