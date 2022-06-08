import { authorizedFor } from "../auth-context";
import { PaddingContainer, FeaturePlugin } from "./imports";
import { Contexts } from "./pages/contexts/Contexts";
import { EventActivations } from "./pages/event-activation/EventActivations";
import { TimePeriodActivations } from "./pages/time-period-activation/TimePeriodActivations";
import AlarmOnIcon from '@mui/icons-material/AlarmOn';
export * from './exports';

export function getPluginDefinition(): FeaturePlugin {
  return {
    id: 'Contexts',
    type: 'FeaturePlugin',
    menuItems: [
      {
        label: "Contexts",
        path: "/contexts",
        icon: <AlarmOnIcon />,
        shouldShow: authorizedFor({ roles: ['User'] })
      },
    ],
    pages: [
      {
        component: <PaddingContainer>
            <Contexts />
          </PaddingContainer>,
        path: 'groups/:groupId/contexts',
        shouldShow: authorizedFor({ roles: ['Admin'] })
      },
      {
        component: <PaddingContainer>
            <Contexts />
          </PaddingContainer>,
        path: 'contexts',
        shouldShow: authorizedFor({ roles: ['User'] })
      },
      {
        component: <PaddingContainer>
            <EventActivations type="activators" />
          </PaddingContainer>,
        path: 'groups/:groupId/contexts/:contextId/event-activators',
        shouldShow: authorizedFor()
      },
      {
        component: <PaddingContainer>
            <EventActivations type="deactivators" />
          </PaddingContainer>,
        path: 'groups/:groupId/contexts/:contextId/event-deactivators',
        shouldShow: authorizedFor()
      },
      {
        component: <PaddingContainer>
            <TimePeriodActivations type="activators" />
          </PaddingContainer>,
        path: 'groups/:groupId/contexts/:contextId/time-period-activators',
        shouldShow: authorizedFor()
      },
      {
        component: <PaddingContainer>
            <TimePeriodActivations type="deactivators" />
          </PaddingContainer>,
        path: 'groups/:groupId/contexts/:contextId/time-period-deactivators',
        shouldShow: authorizedFor()
      },
    ]
  }
}