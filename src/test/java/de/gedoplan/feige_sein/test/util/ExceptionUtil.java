package de.gedoplan.feige_sein.test.util;

import javax.ejb.EJBException;

/**
 * Utility-Klasse mit diversen Hilfsmethoden für Exceptions.
 * 
 * @author dw
 */
public final class ExceptionUtil {
  /**
   * Exception eines bestimmten Typs aus der Exception Chain holen.
   * 
   * @param t
   *          Throwable als Start der Exception Chain
   * @param exceptionClass
   *          gesuchte Exception-Klasse
   * @return gefundene Exception oder <code>null</code>, wenn nicht in Exception Chain
   */
  @SuppressWarnings("unchecked")
  public static <E> E getException(Throwable t, Class<E> exceptionClass) {
    if (exceptionClass.isInstance(t)) {
      return (E) t;
    }

    Throwable cause = t.getCause();
    if (cause == null) {
      return null;
    }

    return getException(cause, exceptionClass);
  }

  public static Throwable getRootException(Throwable t) {
    if (t == null) {
      return null;
    }

    if (t instanceof EJBException) {
      return getRootException(t.getCause());
    }

    return t;
  }

  private ExceptionUtil() {
  }

}
