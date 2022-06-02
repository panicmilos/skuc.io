import axios from 'axios';


export type SocketIoParams = {
  host: string
  namespace: string
  path: string
  serverHost: string

};

export class SocketIoClient {
  protected host: string;
  protected namespace: string;
  protected path: string;
  protected serverHost: string;
  protected client: any;

  constructor({ host, namespace, path, serverHost }: SocketIoParams) {
    this.host = host;
    this.namespace = namespace;
    this.path = path;
    this.serverHost = serverHost;
  }

  init() {

    axios.post(`${this.serverHost}/sockets-initialization/${this.namespace}`)
    .then(resp => {
      this.client = (window as any).io.connect(`${this.host}/${this.namespace}`, {
        forceNew: true,
        transports: ['polling', 'websocket']
      });
  
      this.addEventListener('connect', () => {
        console.log(`Connected to ${this.namespace} namespace.`);
      });
  
      this.addEventListener('disconnect', () => {
        console.log(`Disconnected from ${this.namespace} namespace.`);
      });

      this.onInitHook();
    })
    .catch(e => console.log('Namespace creation failed.'));
  }

  onInitHook() {}

  addEventListener(event: string, listener: (e: any) => void) {
    this.client.on(event, listener);
  }

  clearEventListeners(event: string) {
    this.client.off(event);
  }

  close() {
    this.client.close();
  }
};