package org.vaadin.addon.formbinder;

import com.vaadin.data.Item;
import com.vaadin.ui.Component;
import com.vaadin.ui.Field;
import com.vaadin.ui.FormFieldFactory;

/**
 * <p>
 * A helper class to create a "reflection powered" FormFieldFactory for
 * "pre created fields" pattern.
 * <p>
 * WHAT IS "PRE CREATED FIELDS PATTERN"
 * <p>
 * Normally when using Vaadin forms, fields are created for item's properties on
 * demand. The task of creating fields for properties is delegated to a
 * FormFieldFactory. These fields are then added to the Form's layout. This
 * approach is usually fine and thanks to customizable {@link FormFieldFactory}
 * and layout the developer can customize the Form rather well.
 * <p>
 * In practice things may get rather messy and complex if the the layout becomes
 * multilevel or it is e.g. designed with Vaadin Visualeditor. Lots of custom
 * logic is needed to overridden attachField/detachField methods etc.
 * <p>
 * In pre created fields pattern responsibilities of field factory is narrowed.
 * Instead of creating new fields for requested properties, its job is only to
 * connect properties to existing fields (created manually by developer) in the
 * form (or its layout).
 * <p>
 * With pre created fields pattern the Form also needs some minor changes: it
 * must be modified to avoid attaching and detaching fields automatically as the
 * developer has already attached them to their appropriate location in the
 * layout. Form changes are practically replacing attachField and detachField
 * implementations with empty methods.
 * <p>
 * WHAT DOES PreCreatedFieldshelper/ViewBoundForm DO
 * <p>
 * Pre created fields pattern is rather simple to implement manually. The field
 * factory however normally needs a considerable amount of boilerplate code to
 * work. PreCreatedFieldsHelper can be used to avoid this boilerplate code by
 * using simple naming convention and some java reflect magic.
 * <p>
 * Practically PreCreatedFieldsHelper is a FormFieldFactory that connects
 * properties to corresponding fields from "target" classes (most often
 * subclasses of form/layouts or composites built with Vaadin Visualdesigner).
 * The connecting is done based on Fields name (java fieldname). The helper
 * class can be used as is or behind the scenes by using ViewBoundForm
 * directly.
 * 
 */
public class PreCreatedFieldsHelper implements FormFieldFactory {

	private Object[] fieldSources;

	/**
	 * Creates a field factory that seeks given field sources fields named
	 * according to convention for related propertyId.
	 * 
	 * @param fieldSources
	 *            class(s) from which fields will be searched
	 */
	public PreCreatedFieldsHelper(Object... fieldSources) {
		this.fieldSources = fieldSources;
	}

	/**
	 * Return fields to Form, using optionally the naming pattern (POJO
	 * Fieldname + "Field") or configurated annotations.
	 * 
	 * @see com.vaadin.ui.FormFieldFactory#createField(com.vaadin.data.Item,
	 *      java.lang.Object, com.vaadin.ui.Component)
	 */
	public Field createField(Item item, Object propertyId, Component uiContext) {
		// pre-created-fields-pattern
		return findField(propertyId, fieldSources);
	}

	private Field findField(Object propertyId, Object[] fieldSources) {
		for (Object object : fieldSources) {
			if(object == null) {
				continue;
			}
			Field f;
			if(object instanceof Object[]) {
				Object[] objs = (Object[]) object;
				f = findField(propertyId, objs);
			} else {
				f = findField(object, propertyId);
			}
			if(f != null) {
				return f;
			}
		}
		return null;
	}
	
	private Field findField(Object object, Object propertyId) {
		Field f;
		final Class<?> viewClass = object.getClass();
		if (viewClass.isAnnotationPresent(FormView.class)
				&& viewClass.getAnnotation(FormView.class).matchFieldsBy()
						.equals(FormFieldMatch.ANNOTATION)) {
			f = getFieldFromAnnotatedView(object, propertyId);
		} else {
			f = getFieldFromNonAnnotatedView(object, propertyId);
		}
		return f;

	}

	/**
	 * Return annoted fields with the right propertyIds.
	 * 
	 * @param view
	 * @param propertyId
	 * @return
	 */
	private Field getFieldFromAnnotatedView(Object view, Object propertyId) {
		java.lang.reflect.Field fields[] = view.getClass().getDeclaredFields();
		for (int i = 0; i < fields.length; i++) {
			// Getting reflect info class of the field
			java.lang.reflect.Field reflectField = fields[i];
			reflectField.setAccessible(true);
			try {
				Object viewField = reflectField.get(view);
				if (reflectField.isAnnotationPresent(PropertyId.class)
						&& reflectField.getAnnotation(PropertyId.class)
								.value()
								.equals(String.valueOf(propertyId))) {
					if (viewField instanceof Field) {
						return (Field) viewField;
					}
				}
			} catch (SecurityException e) {
				throw new RuntimeException(
						"Field coudn't be accessed, check security manager settings.",
						e);
			} catch (IllegalArgumentException e) {

			} catch (IllegalAccessException e) {
				throw new RuntimeException(
						"Field coudn't be accessed, check security manager settings.",
						e);
			}
		}
		return null;
	}

	/**
	 * Return fields based on naming pattern (POJO fields + Field)
	 * 
	 * @param view
	 * @param propertyId
	 * @return
	 */
	private Field getFieldFromNonAnnotatedView(Object view, Object propertyId) {
		try {
			java.lang.reflect.Field field = view.getClass().getDeclaredField(
					propertyId.toString() + "Field");
			field.setAccessible(true);
			Object f = field.get(view);
			if (f instanceof Field) {
				return (Field) f;
			}
		} catch (SecurityException e) {
			throw new RuntimeException(
					"Field coudn't be accessed, check security manager settings.",
					e);
		} catch (NoSuchFieldException e) {
			// NOP try from next source
		} catch (IllegalArgumentException e) {
			// NOP try from next source
		} catch (IllegalAccessException e) {
			throw new RuntimeException(
					"Field coudn't be accessed, check security manager settings.",
					e);
		}
		return null;
	}

}