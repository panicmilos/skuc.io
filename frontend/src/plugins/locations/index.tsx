import { PaddingContainer, FeaturePlugin } from "./imports";
import { Locations } from "./pages/locations/Locations";
import EditLocationIcon from '@mui/icons-material/EditLocation';
import { authorizedFor } from "../auth-context";

export * from './exports';

export function getPluginDefinition(): FeaturePlugin {
  return {
    id: 'Locations',
    type: 'FeaturePlugin',
    menuItems: [
      {
        label: "Locations",
        path: "/locations",
        icon: <EditLocationIcon />,
        shouldShow: authorizedFor({ roles: ['User'] })
      },
    ],
    pages: [
      {
        component: <PaddingContainer>
            <Locations/>
          </PaddingContainer>,
        path: 'groups/:groupId/locations',
        shouldShow: authorizedFor({ roles: ['Admin'] })
      },
      {
        component: <PaddingContainer>
            <Locations/>
          </PaddingContainer>,
        path: 'locations',
        shouldShow: authorizedFor({ roles: ['User'] })
      }
    ]
  }
}