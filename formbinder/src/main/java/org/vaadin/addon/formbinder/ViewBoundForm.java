package org.vaadin.addon.formbinder;

import com.vaadin.ui.ComponentContainer;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Field;
import com.vaadin.ui.Form;
import com.vaadin.ui.Layout;

/**
 * A customized Form implementation that uses pre created fields pattern by
 * default. Fields for item properties are searched from component's or its
 * layout's (java) fields.
 * <p>
 * Connection between field and property is made based on naming convention:
 * e.g. property with id "weight" maps to field named "weightField". So for each
 * propertyId that is wished to be editable in the form there should be
 * appropriately named {@link Field} in either subclass of ViewBoundForm or in
 * the layout/content used by it. E.g. a simple pojo like this:
 * 
 * <pre>
 * <code>
 * public class MyPojo {
 * 		String foo;
 * 		// getters, setters
 * }
 * </code>
 * </pre>
 * 
 * can be connected to a custom form like this:
 * 
 * <pre>
 * <code>
 * public class MyPojoForm extends ViewBoundForm {
 * 		
 * 		// this will be connected to field "foo" in MyPojo
 * 		public TextField fooField = new TextField();
 * 
 * 		public MyPojoForm {
 * 			// Note, most commonly more complex layout 
 * 			// or separate view class is used
 * 			getLayout().addComponent(fooField);
 * 		}
 * 
 * }
 * </code>
 * </pre>
 * 
 * <p>
 * If the naming convention cannot be used for some reason, fields can be bound
 * with {@link PropertyId} annotations. Also not that in this case the class
 * containing fields must also be configured with {@link FormView} annotation.
 * 
 * @see PreCreatedFieldsHelper
 * @see Form
 */
public class ViewBoundForm extends Form {

	private Object[] customFieldSources;

	public ViewBoundForm() {
		setFormFieldFactory(new PreCreatedFieldsHelper(this));
	}

	public ViewBoundForm(ComponentContainer layout) {
		setContent(layout);
	}

	/**
	 * Sets the layout (aka view) of this form. The layout is used as a primary
	 * source for pre created fields, the actual form as a secondary source.
	 * 
	 * @param layout
	 */
	public void setContent(ComponentContainer layout) {
		if (layout instanceof Layout) {
			super.setLayout((Layout) layout);
		} else {
			// form only accepts Layout as content so wrap into CssLayout if
			// necessary (e.g. CustomComponent)
			CssLayout cssLayout = new CssLayout();
			cssLayout.addComponent(layout);
			if(layout.getWidthUnits() == UNITS_PERCENTAGE) {
				cssLayout.setWidth("100%");
			}
			if(layout.getHeightUnits() == UNITS_PERCENTAGE) {
				cssLayout.setHeight("100%");
			}
			super.setLayout(cssLayout);
		}
		setFormFieldFactory(new PreCreatedFieldsHelper(layout, this,
				customFieldSources));
	}

	/**
	 * @param fieldSource
	 *            custom source object(s) from which fields are searched for. Layout and
	 *            the form itself are automatically searched.
	 */
	public void setCustomFieldSources(Object... fieldSource) {
		this.customFieldSources = fieldSource;
		if(getFormFieldFactory() instanceof PreCreatedFieldsHelper) {
			setFormFieldFactory(new PreCreatedFieldsHelper(getLayout(), this,
					customFieldSources));
		}
	}

	/**
	 * Gets custom field sources set for this form.
	 */
	public Object[] getCustomFieldSouces() {
		return customFieldSources;
	}

	@Override
	public void setLayout(Layout newLayout) {
		if (newLayout == null) {
			super.setLayout(newLayout);
		} else {
			setContent(newLayout);
		}
	}

	@Override
	protected void attachField(Object propertyId, Field field) {
		// NOP as fields are expected to be attached via view
	}

	@Override
	protected void detachField(Field field) {
		// NOP as fields are expected to be attached via view
	}
}
