import { PaddingContainer, FeaturePlugin } from "./imports";
import GroupIcon from '@mui/icons-material/Group';
import { Groups } from "./pages/Groups/Groups";

export function getPluginDefinition(): FeaturePlugin {
  return {
    id: 'Groups',
    type: 'FeaturePlugin',
    menuItems: [
      {
        label: 'Groups',
        path: 'groups',
        icon: <GroupIcon/>
      }
    ],
    pages: [
      {
        component: <PaddingContainer>
            <Groups/>
          </PaddingContainer>,
        path: 'groups'
      }
    ]
  }
}