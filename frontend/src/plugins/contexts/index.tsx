import { PaddingContainer, FeaturePlugin } from "./imports";
import { Contexts } from "./pages/contexts/Contexts";

export * from './exports';

export function getPluginDefinition(): FeaturePlugin {
  return {
    id: 'Contexts',
    type: 'FeaturePlugin',
    menuItems: [
    ],
    pages: [
      {
        component: <PaddingContainer>
            <Contexts />
          </PaddingContainer>,
        path: 'groups/:groupId/contexts'
      }
    ]
  }
}