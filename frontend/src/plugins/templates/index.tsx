import { PaddingContainer, FeaturePlugin } from "./imports";
import { Templates } from "./pages/templates/Templates";

export * from './exports';

export function getPluginDefinition(): FeaturePlugin {
  return {
    id: 'Templates',
    type: 'FeaturePlugin',
    menuItems: [
    ],
    pages: [
      {
        component: <PaddingContainer>
            <Templates />
          </PaddingContainer>,
        path: 'groups/:groupId/templates'
      },
    ]
  }
}