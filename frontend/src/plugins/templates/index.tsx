import { PaddingContainer, FeaturePlugin } from "./imports";
import { TemplateInstances } from "./pages/template-instances/TemplateInstances";
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
      {
        component: <PaddingContainer>
            <TemplateInstances />
          </PaddingContainer>,
        path: 'groups/:groupId/templates/:templateId/instances'
      },
    ],
    
  }
}