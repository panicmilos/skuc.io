import LiveTvIcon from '@mui/icons-material/LiveTv';
import { PaddingContainer, FeaturePlugin } from "./imports";
import { Live } from './pages/Live/Live';
import AssessmentIcon from '@mui/icons-material/Assessment';
import { Reports } from './pages/Reports/Reports';

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
      
    ]
  }
}