import { PaddingContainer, FeaturePlugin } from "./imports";
import { Locations } from "./pages/locations/Locations";

export * from './exports';

export function getPluginDefinition(): FeaturePlugin {
  return {
    id: 'Locations',
    type: 'FeaturePlugin',
    menuItems: [
      
    ],
    pages: [
      {
        component: <PaddingContainer>
            <Locations/>
          </PaddingContainer>,
        path: 'groups/:groupId/locations'
      }
    ]
  }
}