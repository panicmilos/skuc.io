import { authorizedFor } from "../auth-context";
import { PaddingContainer, FeaturePlugin } from "./imports";
import { TemplateInstances } from "./pages/template-instances/TemplateInstances";
import { Templates } from "./pages/templates/Templates";
import HomeRepairServiceIcon from '@mui/icons-material/HomeRepairService';

export * from './exports';

export function getPluginDefinition(): FeaturePlugin {
  return {
    id: 'Templates',
    type: 'FeaturePlugin',
    menuItems: [
      {
        label: "Templates",
        path: "/templates",
        icon: <HomeRepairServiceIcon />,
        shouldShow: authorizedFor({ roles: ['User'] })
      },
    ],
    pages: [
      {
        component: <PaddingContainer>
            <Templates />
          </PaddingContainer>,
        path: 'groups/:groupId/templates',
        shouldShow: authorizedFor({ roles: ['Admin']})
      },
      {
        component: <PaddingContainer>
            <Templates />
          </PaddingContainer>,
        path: 'templates',
        shouldShow: authorizedFor({ roles: ['User']})
      },
      {
        component: <PaddingContainer>
            <TemplateInstances />
          </PaddingContainer>,
        path: 'groups/:groupId/templates/:templateId/instances',
        shouldShow: authorizedFor()
      },
    ],
    
  }
}