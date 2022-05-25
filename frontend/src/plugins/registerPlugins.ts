import { Plugin } from '../core/Context';

export const registerPlugins = (registerPlugins: (plugins: Plugin[]) => any) => {
  import('.').then((module: { [key: string]: () => Plugin }) => {
    const plugins = Object.keys(module).map((pluginKey: string) => {
      const getPluginDefinition = module[pluginKey];
      return getPluginDefinition();
    });
    registerPlugins(plugins);
  });
}