import LiveTvIcon from '@mui/icons-material/LiveTv';
import { PaddingContainer, FeaturePlugin } from "./imports";
import { Live } from './pages/Live/Live';
import AssessmentIcon from '@mui/icons-material/Assessment';
import { Reports } from './pages/Reports/Reports';
import AddCircleIcon from '@mui/icons-material/AddCircle';
import { PredefinedReports } from './pages/PredefinedReports/PredefinedReports';
import { QuickReports } from './pages/QuickReports/QuickReports';
import { authorizedFor } from '../auth-context';

export * from './exports';

export function getPluginDefinition(): FeaturePlugin {
  return {
    id: 'Reports',
    type: 'FeaturePlugin',
    menuItems: [
      {
        label: 'Live',
        path: 'live',
        icon: <LiveTvIcon/>,
        shouldShow: authorizedFor({ roles: ['User'] })
      },
      {
        label: 'Reports',
        path: 'reports',
        icon: <AssessmentIcon/>,
        shouldShow: authorizedFor({ roles: ['User'] })
      },
      {
        label: 'Quick Reports',
        path: 'quick-reports',
        icon: <AssessmentIcon/>,
        shouldShow: authorizedFor({ roles: ['User'] })
      },
      {
        label: 'Predefined Reports',
        path: 'predefined-reports',
        icon: <AddCircleIcon/>,
        shouldShow: authorizedFor({ roles: ['User'] })
      },
    ],
    pages: [
      {
        component: <PaddingContainer>
            <Live />
          </PaddingContainer>,
        path: 'live',
        shouldShow: authorizedFor({ roles: ['User'] })
      },
      {
        component: <PaddingContainer>
            <Reports />
          </PaddingContainer>,
        path: 'reports',
        shouldShow: authorizedFor({ roles: ['User'] })
      },
      {
        component: <PaddingContainer>
            <QuickReports />
          </PaddingContainer>,
        path: 'quick-reports',
        shouldShow: authorizedFor({ roles: ['User'] })
      },
      {
        component: <PaddingContainer>
            <PredefinedReports />
          </PaddingContainer>,
        path: 'predefined-reports',
        shouldShow: authorizedFor({ roles: ['User'] })
      },
    ]
  }
}