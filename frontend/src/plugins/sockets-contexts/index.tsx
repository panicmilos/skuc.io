import { ContextPlugin } from "./imports";
import { SocketsContextProvider } from "./Context";


export function getPluginDefinition(): ContextPlugin {
  return {
    id: 'SocketsContext',
    type: 'ContextPlugin',
    Provider: SocketsContextProvider
  }
}