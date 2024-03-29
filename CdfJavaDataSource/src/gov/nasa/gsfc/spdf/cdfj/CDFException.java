package gov.nasa.gsfc.spdf.cdfj;
/**
 * Base class for exceptions thrown by methods in this package.
 */
public class CDFException extends Exception {
    public CDFException(String message) {
        super(message);
    }

    /**
     * Exceptions thrown by methods of CDFReader and its superclasses.
     */
    public static final class ReaderError extends CDFException {
        public ReaderError(String message) {
            super(message);
        }
        public ReaderError(String message,Throwable initCause) {
            super(message);
            initCause(initCause);
        }
        public ReaderError(Throwable initCause) {
            super(initCause.getMessage());
            initCause(initCause);
        }        
    }
    /**
     * Exceptions thrown by methods of CDFWriter and its superclass.
     */
    public static final class WriterError extends CDFException {
        public WriterError(String message) {
            super(message);
        }
        public WriterError(String message,Throwable initCause) {
            super(message);
            initCause(initCause);
        }
    }

    /**
     * Exceptions thrown by methods of CDFReader to indicate absence of
     * data for a variable.
     */
    public static class NoRecords extends CDFException {
        public NoRecords(String varName) {
            super("Variable " + varName + " has no records.");
        }
    }
}
