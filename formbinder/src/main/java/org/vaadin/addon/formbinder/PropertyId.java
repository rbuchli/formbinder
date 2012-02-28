package org.vaadin.addon.formbinder;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.vaadin.ui.Field;

/**
 * Annotation to configure field property to be binded.
 * 
 * @author Eduardo Frazao - edufrazao@gmail.com
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface PropertyId {

	/**
	 * @return the property id to which the annotated {@link Field} should be
	 *         bound
	 */
	String value();
}
