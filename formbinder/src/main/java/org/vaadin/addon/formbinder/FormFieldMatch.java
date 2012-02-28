package org.vaadin.addon.formbinder;

import java.io.Serializable;

/**
 * Defines how the PreCreatedView fields must be reached.
 * @author Eduardo Frazao - edufrazao@gmail.com
 * 
 */
public enum FormFieldMatch implements Serializable {
    NAMING_CONVENTION,
    ANNOTATION
}
