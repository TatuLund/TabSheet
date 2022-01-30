package org.vaadin.addons.tatu;

import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.littemplate.LitTemplate;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.tabs.TabsVariant;
import com.vaadin.flow.component.template.Id;
import com.vaadin.flow.router.Route;

@Route(value = "template", layout = MainLayout.class)
@JsModule("./tab-sheet-view.ts")
@Tag("tab-sheet-view")
public class TemplateView extends LitTemplate {

	@Id("tabsheet")
	TabSheet tabSheet;
	
	@Id("sheet1")
	Div sheet1;
	
	@Id("sheet2")
	Div sheet2;

	public TemplateView() {
		tabSheet.setCaption("tab0", "First tab");
		tabSheet.addTabChangedListener(event -> {
			Notification.show("Tab "+event.getIndex());
			if (event.getIndex() == 2) {
				tabSheet.removeTab("tab2");
			}
		});
		
	}
}
