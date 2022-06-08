import { PaddingContainer, FeaturePlugin } from "./imports";
import GroupIcon from '@mui/icons-material/Group';
import { Groups } from "./pages/Groups/Groups";
import { authorizedFor } from "../auth-context";

export function getPluginDefinition(): FeaturePlugin {
  return {
    id: 'Groups',
    type: 'FeaturePlugin',
    menuItems: [
      {
        label: 'Groups',
        path: 'groups',
        icon: <GroupIcon/>,
        shouldShow: authorizedFor({ roles: ['Admin'] })
      }
    ],
    pages: [
      {
        component: <PaddingContainer>
            <Groups/>
          </PaddingContainer>,
        path: 'groups',
        shouldShow: authorizedFor({ roles: ['Admin'] })
      }
    ]
  }
}