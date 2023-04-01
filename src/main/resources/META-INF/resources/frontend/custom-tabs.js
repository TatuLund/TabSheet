import { PolymerElement, html } from '@polymer/polymer/polymer-element.js';
import { Tabs } from '@vaadin/tabs/vaadin-tabs.js';

export class CustomTabs extends Tabs {
  static get is() {
    return 'custom-tabs'
  }

  static get template () {
    return html`
      <style>
        :host([orientation="horizontal"][theme~="bordered"]) [part="tabs"] {
          gap: var(--lumo-space-s);
        }
        :host([orientation="vertical"]) [part='tabs'] {
          margin: 0px !important;
        }
      </style>
        ${super.template}
      `;
  }
}

customElements.define('custom-tabs', CustomTabs);