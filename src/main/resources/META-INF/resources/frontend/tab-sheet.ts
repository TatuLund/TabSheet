import '@vaadin/vaadin-tabs';
import { customElement, property, css, html, LitElement } from 'lit-element';

@customElement('tab-sheet')
export class TabSheet extends LitElement {

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
    this._doSelectTab(page);
	const event = new CustomEvent('tab-changed', {
		detail: page,
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

  getTabIndex(tab : string) : number {
	return this._getSlots().findIndex(t => t === tab);
  }

  getTabCaption(tab : string) : string {
    const index = this._getSlots().findIndex(t => t === tab);
    return this._getCaptions()[index];
  }

  addTab(caption : string, element : HTMLElement, tab  : string) {
	element.setAttribute("tabcaption",caption)
	element.setAttribute("slot",tab);
	this.appendChild(element);
  }

  removeTab(tab : string) {
	const page = this.getTabIndex(tab);
	this.removeChild(this._getElements()[page]);
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

  _getSlots() : string[] {
	const slots: string[] = [];
	for (var i=0; i < this.children.length; i++) {
		const element = this.children.item(i);
		const slot = element?.getAttribute("slot");
		if (slot) {
			slots.push(slot);
		} else {
			slots.push("tab"+i);
		}
	}
	return slots;
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
        <vaadin-tabs .selected=${this.selected} @selected-changed="${this.selectedChanged}">
          ${this._getCaptions().map(
	        (caption) => html`<vaadin-tab>${caption}</vaadin-tab>` 
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