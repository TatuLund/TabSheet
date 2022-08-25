import {PolymerElement, html} from '@polymer/polymer/polymer-element.js';
//import './tab-sheet.ts';

class TabSheetTemplate extends PolymerElement {
    static get is() {
        return 'tab-sheet-template';
    }

    static get properties() {
        return {
            // Declare your properties here.
        };
    }

    _attachDom(dom) {
       this.appendChild(dom);
    }

    static get template() {
        return html`
          <tab-sheet theme="icon-on-top" id="tabsheet">
            <div id="sheet1" tabicon="vaadin:dashboard" tabcaption="Sheet 1">This content for the first tab</div>
            <div id="sheet2" tabicon="vaadin:database" tabcaption="Sheet 2">Second tab has different content</div>
            <div id="sheet3" tabicon="vaadin:trash" tabcaption="Sheet 3">Third to be removed</div>
          </tab-sheet>
        `
    }
}
customElements.define(TabSheetTemplate.is, TabSheetTemplate);
