import { PaddingContainer, FeaturePlugin } from "./imports";
import { Devices } from "./pages/devices/Devices";

export function getPluginDefinition(): FeaturePlugin {
  return {
    id: 'Devices',
    type: 'FeaturePlugin',
    menuItems: [
    ],
    pages: [
      {
        component: <PaddingContainer>
            <Devices/>
          </PaddingContainer>,
        path: 'groups/:groupId/locations/:locationId/devices'
      }
    ]
  }
}