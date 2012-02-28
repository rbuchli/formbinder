package org.vaadin.addon.formbinder;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation to configure a FormView. Its not mandatory, and not using it, will
 * cause fields to be matched by their name using the default naming convention.
 * For the case that you can't / don't want use the naming convention you can
 * use this annotation to inform the PreCreatedForm to match annotated fields,
 * and configure their propertyIds.
 * 
 * @author Eduardo Frazao - edufrazao@gmail.com
 * 
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface FormView {
	FormFieldMatch matchFieldsBy() default FormFieldMatch.NAMING_CONVENTION;
}
