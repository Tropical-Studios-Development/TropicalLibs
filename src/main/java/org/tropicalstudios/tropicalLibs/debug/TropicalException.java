package org.tropicalstudios.tropicalLibs.debug;

public class TropicalException extends RuntimeException {

    // Should we save thrown exceptions to error.log file automatically when they are thrown?
    private static final boolean saveErrorAutomatically = true;

    /**
     * Create a new exception and logs it
     *
     * @param throwable  The throwable that should be sent
     */
    public TropicalException(Throwable throwable) {
        super(throwable);

        if (saveErrorAutomatically)
            Debugger.saveError(throwable);
    }

    /**
     * Create a new exception and logs it
     *
     * @param message   The message that should be sent
     */
    public TropicalException(String message) {
        super(message);

        if (saveErrorAutomatically)
            Debugger.saveError(this, message);
    }
}
