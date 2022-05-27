import { PaddingContainer, FeaturePlugin } from "./imports";
import { Contexts } from "./pages/contexts/Contexts";
import { EventActivations } from "./pages/event-activation/EventActivations";
import { TimePeriodActivations } from "./pages/time-period-activation/TimePeriodActivations";

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
      },
      {
        component: <PaddingContainer>
            <EventActivations type="activators" />
          </PaddingContainer>,
        path: 'groups/:groupId/contexts/:contextId/event-activators'
      },
      {
        component: <PaddingContainer>
            <EventActivations type="deactivators" />
          </PaddingContainer>,
        path: 'groups/:groupId/contexts/:contextId/event-deactivators'
      },
      {
        component: <PaddingContainer>
            <TimePeriodActivations type="activators" />
          </PaddingContainer>,
        path: 'groups/:groupId/contexts/:contextId/time-period-activators'
      },
      {
        component: <PaddingContainer>
            <TimePeriodActivations type="deactivators" />
          </PaddingContainer>,
        path: 'groups/:groupId/contexts/:contextId/time-period-deactivators'
      },
    ]
  }
}