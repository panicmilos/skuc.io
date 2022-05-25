import { Users } from "./pages/Users/Users";
import PersonIcon from '@mui/icons-material/Person';
import { UserProfile } from "./pages/UserProfile/UserProfile";
import { PaddingContainer, FeaturePlugin } from "./imports";

export * from './exports';

export function getPluginDefinition(): FeaturePlugin {
  return {
    id: 'Users',
    type: 'FeaturePlugin',
    menuItems: [
      {
        label: 'Profile',
        path: 'profile',
        icon: <PersonIcon/>
      }
    ],
    pages: [
      {
        component: <PaddingContainer>
            <Users/>
          </PaddingContainer>,
        path: 'groups/:groupId/users'
      },
      {
        component: <PaddingContainer>
            <UserProfile/>
          </PaddingContainer>,
        path: 'profile'
      }
    ]
  }
}