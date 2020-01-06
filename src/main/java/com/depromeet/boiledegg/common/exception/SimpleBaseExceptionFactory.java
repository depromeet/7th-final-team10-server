package com.depromeet.boiledegg.common.exception;

import org.springframework.stereotype.Component;

@Component
final class SimpleBaseExceptionFactory implements BaseExceptionFactory {

    @Override
    public BaseException create(final ErrorStatus errorStatus) {
        return SimpleBaseException.of(errorStatus);
    }

    private static class SimpleBaseException extends BaseException {

        private final ErrorStatus errorStatus;

        private SimpleBaseException(final ErrorStatus errorStatus) {
            super(errorStatus.getMessage());
            this.errorStatus = errorStatus;
        }

        private static BaseException of(final ErrorStatus errorStatus) {
            return new SimpleBaseException(errorStatus);
        }

        @Override
        public ErrorStatus getErrorStatus() {
            return errorStatus;
        }
    }
}
