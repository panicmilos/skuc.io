import { Users } from "./pages/Users/Users";
import PersonIcon from '@mui/icons-material/Person';
import GroupIcon from '@mui/icons-material/Group';
import { UserProfile } from "./pages/UserProfile/UserProfile";
import { PaddingContainer, FeaturePlugin } from "./imports";
import { authorizedFor } from "../auth-context";

export * from './exports';

export function getPluginDefinition(): FeaturePlugin {
  return {
    id: 'Users',
    type: 'FeaturePlugin',
    menuItems: [
      {
        label: 'Users',
        path: 'users',
        icon: <GroupIcon/>,
        shouldShow: authorizedFor({ roles: ['User'] })
      },
      {
        label: 'Profile',
        path: 'profile',
        icon: <PersonIcon/>,
        shouldShow: authorizedFor()
      }
    ],
    pages: [
      {
        component: <PaddingContainer>
            <Users/>
          </PaddingContainer>,
        path: 'groups/:groupId/users',
        shouldShow: authorizedFor({ roles: ['Admin'] })
      },
      {
        component: <PaddingContainer>
            <Users/>
          </PaddingContainer>,
        path: 'users',
        shouldShow: authorizedFor({ roles: ['User'] })
      },
      {
        component: <PaddingContainer>
            <UserProfile/>
          </PaddingContainer>,
        path: 'profile',
        shouldShow: authorizedFor()
      }
    ]
  }
}