import { LightningElement } from 'lwc';

const routeMap = {
    "/": "Home",
    "/about": "About"
};

export default class App extends LightningElement {
    static renderMode = 'light';

    _path;

    get path() {
        return routeMap[this._path];
    }

    connectedCallback() {
        this._path = window.location.pathname;
    }
}
