import LiveTvIcon from '@mui/icons-material/LiveTv';
import { PaddingContainer, FeaturePlugin } from "./imports";
import { Live } from './pages/Live/Live';
import AssessmentIcon from '@mui/icons-material/Assessment';
import { Reports } from './pages/Reports/Reports';
import AddCircleIcon from '@mui/icons-material/AddCircle';
import { PredefinedReports } from './pages/PredefinedReports/PredefinedReports';

export * from './exports';

export function getPluginDefinition(): FeaturePlugin {
  return {
    id: 'Reports',
    type: 'FeaturePlugin',
    menuItems: [
      {
        label: 'Live',
        path: 'live',
        icon: <LiveTvIcon/>
      },
      {
        label: 'Reports',
        path: 'reports',
        icon: <AssessmentIcon/>
      },
      {
        label: 'Predefined Reports',
        path: 'predefined-reports',
        icon: <AddCircleIcon/>
      },
    ],
    pages: [
      {
        component: <PaddingContainer>
            <Live />
          </PaddingContainer>,
        path: 'live'
      },
      {
        component: <PaddingContainer>
            <Reports />
          </PaddingContainer>,
        path: 'reports'
      },
      {
        component: <PaddingContainer>
            <PredefinedReports />
          </PaddingContainer>,
        path: 'predefined-reports'
      },
    ]
  }
}