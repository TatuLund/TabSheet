//import './tab-sheet.ts';
import { customElement, property, css, html, LitElement } from 'lit-element';

@customElement('tab-sheet-view')
export class TabSheetView extends LitElement {

  render() {
    return html`
      <tab-sheet id="tabsheet">
         <div id="sheet1" tabcaption="Sheet 1">This content for the first tab</div>
         <div id="sheet2" tabcaption="Sheet 2">Second tab has different content</div>
         <div id="sheet3" tabcaption="Sheet 3">Third to be removed</div>
      </tab-sheet>
    `;
  }
}