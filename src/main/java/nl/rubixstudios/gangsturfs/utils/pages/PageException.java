package nl.rubixstudios.gangsturfs.utils.pages;

class PageException extends RuntimeException {

    public PageException(String message) {
        super(message);
    }

    public PageException(String message, Throwable cause) {
        super(message, cause);
    }

    public PageException(Throwable cause) {
        super(cause);
    }

    protected PageException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}