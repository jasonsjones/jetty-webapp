import { createElement } from 'lwc';
import App from 'x/app';

const elm = createElement('x-app', { is: App});
const body = document.querySelector('body');
body.insertBefore(elm, body.firstChild);
