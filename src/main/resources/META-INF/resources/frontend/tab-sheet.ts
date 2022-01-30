import '@vaadin/vaadin-tabs';
import '@vaadin/vaadin-icon';
import { ThemableMixin } from '@vaadin/vaadin-themable-mixin/vaadin-themable-mixin.js';
import { customElement, property, css, html, LitElement, TemplateResult } from 'lit-element';

@customElement('tab-sheet')
export class TabSheet extends ThemableMixin(LitElement) {

  @property()
  selected = 0;

  static get styles() {
    return css`
        :host {
          display: block;
        }
        :host([hidden]) {
          display: none !important;
        }
        .container {
	      height: inherit;
        }
        .tab {
	      height: inherit;
	      overflow: auto;
	    }
    `;
  }

  selectedChanged(e: CustomEvent) {
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

  _getTabs() : TemplateResult[] {
	const templates: TemplateResult[] = [];
	for (var i=0; i < this.children.length; i++) {
		const element = this.children.item(i);
		const caption = element?.getAttribute("tabcaption");
		const icon = element?.getAttribute("tabicon");
		if (caption && icon) {
			const template = html`<vaadin-tab theme="${this.theme}"><vaadin-icon icon="${icon}"></vaadin-icon>${caption}</vaadin-tab>`
			templates.push(template)
	    } else if (caption) {
			const template = html`<vaadin-tab theme="${this.theme}">${caption}</vaadin-tab>`
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
			element.setAttribute("slot",newSlot);
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

  render() {
    return html`
      <div class="container">
        <vaadin-tabs theme="${this.theme}" .selected=${this.selected} @selected-changed="${this.selectedChanged}">
          ${this._getTabs().map(
	        (template) => template
	      )}
        </vaadin-tabs>
        ${this._getSlots().map((tab) => html`
          <div class="tab" id=${tab} style="display: none">
            <slot name=${tab}>
            </slot>
          </div>`
        )}
      </div>
    `;
  }
}