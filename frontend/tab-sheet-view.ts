//import './tab-sheet.ts';
import { customElement, property, css, html, LitElement } from 'lit-element';

@customElement('tab-sheet-view')
export class TabSheetView extends LitElement {

  render() {
    return html`
      <tab-sheet theme="icon-on-top" id="tabsheet">
         <div id="sheet1" tabicon="vaadin:dashboard" tabcaption="Sheet 1">This content for the first tab</div>
         <div id="sheet2" tabicon="vaadin:database" tabcaption="Sheet 2">Second tab has different content</div>
         <div id="sheet3" tabicon="vaadin:trash" tabcaption="Sheet 3">Third to be removed</div>
      </tab-sheet>
    `;
  }
}