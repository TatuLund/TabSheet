import '@vaadin/tabs';
import '@vaadin/icon';
import '@vaadin/tooltip';
import { ThemableMixin } from '@vaadin/vaadin-themable-mixin/vaadin-themable-mixin.js';
import { css, html, LitElement, TemplateResult, nothing } from 'lit';
import { customElement, property } from 'lit/decorators.js';
import { TabsSelectedChangedEvent } from '@vaadin/vaadin-tabs/vaadin-tabs.js';
import './custom-tabs.js';

@customElement('tab-sheet')
export class TabSheet extends ThemableMixin(LitElement) {

  @property()
  selected = 0;

  @property()
  orientation = "horizontal";

  @property()
  theme : string | null = null;

  static get is() {
    return 'tab-sheet';
  }

  static get styles() {
    return css`
        :host {
          display: block;
        }
        :host([hidden]) {
          display: none !important;
        }
        [part="container"] {
	      display: flex;
          flex-direction: column;
	      height: inherit;
        }
        :host([orientation="vertical"]) [part="container"] {
          flex-direction: row;
        }
        :host([orientation="vertical"][theme~="bordered"]) [part="container"] {
          box-shadow: 0 0 0 1px var(--lumo-contrast-30pct);
          border-radius: var(--lumo-border-radius-m);
        }
        [part="sheet"] {
	      height: inherit;
	      overflow: auto;
          flex-grow: 1;
          width: 100%
	    }
        :host([theme~="bordered"]) [part="sheet"] {
          box-shadow: 0 0 0 1px var(--lumo-contrast-30pct);
          border-radius: var(--lumo-border-radius-m);
	    }
        :host([orientation="vertical"][theme~="bordered"]) [part="sheet"] {
    	  border-top-left-radius: unset;
        }
        [part="tab"] {
	      white-space: nowrap;
        }
        [part="tab"][theme~="bordered"] {
          background: var(--lumo-contrast-10pct);
        }
        [part="tab"][theme~="bordered"][orientation="horizontal"] {
	      border: 1px solid var(--lumo-contrast-30pct); 
          border-bottom: none; 
          border-top-left-radius: var(--lumo-border-radius-m);
          border-top-right-radius: var(--lumo-border-radius-m);
        }
        [part="tab"][theme~="bordered"][selected] {
	      background: var(--lumo-base-color);
          border-left: 1px solid var(--lumo-contrast-30pct);
	    }
	  `;
  }

  selectedChanged(e: TabsSelectedChangedEvent) {
    const page = e.detail.value;
    const tab = this.getTab(page);
    this._doSelectTab(page);
    const details : JSON = <JSON><unknown>{
	  "index": page,
      "caption": this.getTabCaption(tab),
      "tab": tab
    }
	const event = new CustomEvent('tab-changed', {
		detail: details,
        composed: true,
        cancelable: true,
        bubbles: true		
	});
	this.dispatchEvent(event);
  }

  _doSelectTab(page : number) {
	const slots = this._getSlots();
	for (var i=0; i<slots.length; i++) {
		this.shadowRoot?.getElementById(slots[i])?.style?.setProperty("display","none");
	}
    this.shadowRoot?.getElementById(slots[page])?.style?.setProperty("display","block");	
  }

  getTab(index : number) : string {
	return this._getSlots()[index];
  }

  getTabIndex(tab : string) : number {
	return this._getSlots().findIndex(t => t === tab);
  }

  getTabCaption(tab : string) : string {
    const index = this._getSlots().findIndex(t => t === tab);
    return this._getCaptions()[index];
  }

  addTab(caption : string, element : HTMLElement, icon  : string) {
	element.setAttribute("tabcaption",caption)
	element.setAttribute("tabicon",icon);
	this.appendChild(element);
  }

  removeTab(tab : string) {
	const page = this.getTabIndex(tab);
	this.removeChild(this._getElements()[page]);
	this.requestUpdate();
  }

  _getCaptions() : string[] {
	const captions: string[] = [];
	for (var i=0; i < this.children.length; i++) {
		const element = this.children.item(i);
		const caption = element?.getAttribute("tabcaption");
		if (caption) {
			captions.push(caption)
		} else {
			captions.push("");
		}
	}
	return captions;
  }

  _getIcon(icon : string | undefined | null) : TemplateResult {
	return html`${icon ? html`<vaadin-icon icon="${icon}"></vaadin-icon>` : nothing}`;
  }

  _getTooltip(text : string | undefined | null) : TemplateResult {
	return html `${text ? html`<vaadin-tooltip slot="tooltip" text="${text}"></vaadin-tooltip>` : nothing}`;
  }

  _getTabs() : TemplateResult[] {
	const templates: TemplateResult[] = [];
	for (var i=0; i < this.children.length; i++) {
		const element = this.children.item(i);
		const caption = element?.getAttribute("tabcaption");
		const tooltip = element?.getAttribute("tooltip");
		const icon = element?.getAttribute("tabicon");
        if (caption) {
			const template = html`<vaadin-tab part="tab" theme="${this.theme}">${this._getIcon(icon)}${caption}${this._getTooltip(tooltip)}</vaadin-tab>`
			templates.push(template)		
		} else {
			templates.push(html``);
		}
	}
	return templates;
  }

  _getSlots() : string[] {
	const slots: string[] = [];
	for (var i=0; i < this.children.length; i++) {
		const element = this.children.item(i);
		const slot = element?.getAttribute("slot");
		if (slot) {
			slots.push(slot);
		} else {
			const newSlot = "tab"+i;
			element?.setAttribute("slot",newSlot);
			slots.push(newSlot);
		}
	}
	return slots;
  }

  setCaption(index : number, caption : string, tab : string) {
	if (tab) {
		index = this.getTabIndex(tab);
	}
	this._getElements()[index].setAttribute("tabcaption",caption);
	this.requestUpdate();
  }

  setSelectedTab(tab : string) {
	const index = this.getTabIndex(tab);
    this._doSelectTab(index);
	this.selected=index;
  }

  setSelected(index : number) {
    this._doSelectTab(index);
	this.selected=index;
  }

  _getElements() : HTMLElement[] {
	const elements: HTMLElement[] = [];
	for (var i=0; i < this.children.length; i++) {
	  const child = this.children.item(i);
	  if (child) {
        elements.push(child as HTMLElement);
  	  }
	}
    return elements;
  }

  _setTheme(theme : string) {
  }

  render() {
    return html`
      <div part="container" class="container">
        <custom-tabs part="tabs" orientation="${this.orientation}" theme="${this.theme}" .selected=${this.selected} @selected-changed="${this.selectedChanged}">
          ${this._getTabs().map(
	        (template) => template
	      )}
        </custom-tabs>
        ${this._getSlots().map((tab) => html`
          <div class="sheet" part="sheet" id=${tab} style="display: none">
            <slot name=${tab}>
            </slot>
          </div>`
        )}
      </div>
    `;
  }
}