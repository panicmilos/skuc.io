window._drawio_callbacks_ = [];
window._drawio_on_rerenders_ = [];
window._drawio_debounce_timeout_ = 300;
// Util function for finding elements relative to the specified container
const find = (id, tagName, searchText) => {
  const container = document.getElementById(id);
  const nodes = container.querySelectorAll(`${tagName}`);
  const foundNodes = []; 
  for (var i = 0; i < nodes.length; i++) {
    const node = nodes[i];
    if (node.innerHTML.startsWith(searchText)) {
      foundNodes.push(node);
    } else if (node.getAttribute('xlink:href')?.includes(searchText)) {
      node.innerHTML = `${searchText}${node.getAttribute('xlink:href').split(searchText)[1]}`;
      foundNodes.push(node);
    }
  }
  return foundNodes;
}
// Set content of a text element
const setTextTo = (containerId) => (id, textFormatter) => {
  const container = document.getElementById(containerId);
  const element = container.querySelector(`*[elementId='${id}']`);
  if(textFormatter) {
    const newline = '_this$is$a$newline_'
    const formatedText = textFormatter(id, newline);
    element.innerHTML = '';
    formatedText.split(newline).forEach(text => {
      element.innerHTML += text;
      element.appendChild(document.createElement('br'));
    });
  }
}
// Set style of an element
const setStyleTo = (containerId) => (id, key, value) => {
  const container = document.getElementById(containerId);
  const element = container.querySelector(`*[elementId='${id}']`);
  element.style[key] = value;
}
// Element class with state
class Element {
  constructor(containerId, id) {
    this.containerId = containerId;
    this.id = id;

    this.setText = this.setText.bind(this);
    this.setStyle = this.setStyle.bind(this);
    this.hide = this.hide.bind(this);
    this.show = this.show.bind(this);
    this.toPrevState = this.toPrevState.bind(this);

    this.lastSetText = () => {};
    this.lastSetStyles = {};
  }

  setText(textFormatter) {
    this.lastSetText = () => setTextTo(this.containerId)(this.id, textFormatter);
    this.lastSetText();
  }

  setStyle(key, value) {
    this.lastSetStyles[key] = () => setStyleTo(this.containerId)(this.id, key, value);
    this.lastSetStyles[key]();
  }

  hide() {
    this.setStyle('display', 'none');
  }

  show() {
    this.setStyle('display', 'flex');
  }

  toPrevState() {
    this.lastSetText();
    Object.keys(this.lastSetStyles).forEach(key => this.lastSetStyles[key]());
  }

}
// For removing all action listeners
function recreateNode(el) {
  var newEl = el.cloneNode(false);
  while (el.hasChildNodes()) newEl.appendChild(el.firstChild);
  el.parentNode.replaceChild(newEl, el);
  return newEl;
}
// Main function
export function initElements({
  id: containerId,
  idPrefix,
  onClick,
  onRerender
}) {
  // Call the lib init
  window._initialize_drawio_diagrams_ && window._initialize_drawio_diagrams_();
  // Return initElements promise
  return new Promise((res, _) => {
    // Init elems handler
    const initElementNodes = () => {
      const elementNodes = [
        ...find(containerId, 'image', idPrefix),
        ...find(containerId, 'text', idPrefix),
        ...find(containerId, 'font', idPrefix),
        ...find(containerId, 'span', idPrefix),
        ...find(containerId, 'b', idPrefix),
        ...find(containerId, 'i', idPrefix),
        ...find(containerId, 'div', idPrefix),
        ...find(containerId, 'p', idPrefix),
        ...find(containerId, 'a', idPrefix),
      ];
      const elements = {};
      elementNodes.forEach(existingElement => {
        // Remove all event listeners
        const element = recreateNode(existingElement);
        // Set id to element
        const id = element.innerHTML.split(idPrefix)[1];
        element.setAttribute('elementId', id);
        // Form element object
        const elementObject = new Element(containerId, id);
        // Style elements
        elementObject.setStyle('cursor', 'pointer');
        elementObject.setStyle('display', 'flex');
        elementObject.setStyle('width', '100px');
        elementObject.setStyle('height', '100px');
        elementObject.setStyle('alignItems', 'center');
        elementObject.setStyle('justifyContent', 'center');
        elementObject.setStyle('userSelect', 'none');
        elementObject.setStyle('msUserSelect', 'none');
        elementObject.setStyle('mozUserSelect', 'none');
        // Add click event listener
        element.addEventListener('click', function (e) {
          onClick && onClick(elementObject);
        });
        // Add element object to map
        elements[id] = elementObject;
      });
      return elements;
    }
    // Hook to the lib
    window._drawio_callbacks_?.push(() => {
      const elements = initElementNodes();
      window._drawio_on_rerenders_?.push(() => {
        Object.keys(elements).forEach(id => elements[id].toPrevState());
        onRerender && onRerender(elements, setTextTo(containerId), setStyleTo(containerId));
      });
      // Resolve promise
      elements && Object.keys(elements).length && res({
        elements,
        setTextTo: setTextTo(containerId),
        setStyleTo: setStyleTo(containerId),
        hide: (id) => setStyleTo(containerId)(id, 'display', 'none'),
        show: (id) => setStyleTo(containerId)(id, 'display', 'block'),
      });
      // Initial render cb
      onRerender && onRerender(elements, setTextTo(containerId), setStyleTo(containerId));
    });
  })
}