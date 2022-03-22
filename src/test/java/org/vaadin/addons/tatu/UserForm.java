package org.vaadin.addons.tatu;

import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.polymertemplate.PolymerTemplate;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.templatemodel.TemplateModel;

@Route("tab-sheet-template")
@JsModule("./tab-sheet-template.js")
@Tag("tab-sheet-template")
public class UserForm extends PolymerTemplate<TemplateModel> {
    
}