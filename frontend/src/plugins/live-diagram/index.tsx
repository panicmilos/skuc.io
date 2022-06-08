import { FeaturePlugin } from "./imports";
import LoginIcon from "@mui/icons-material/Login";
import { Diagram } from "./Diagram";
import { PaddingContainer } from "../sidebar";
import { authorizedFor, getGroupIdFromToken } from "../auth-context";

export function getPluginDefinition(): FeaturePlugin {
  return {
    id: "LiveDiagram",
    type: "FeaturePlugin",
    menuItems: [
      {
        label: "Diagram",
        path: "/diagrams",
        icon: <LoginIcon />,
        shouldShow: authorizedFor({ roles: ['User'] })
      },
    ],
    pages: [
      {
        component: (
          <PaddingContainer>
            <Diagram diagramFilePath="/diagrams/diagram.drawio.xml" groupId={getGroupIdFromToken()} />
          </PaddingContainer>
        ),
        path: "/diagrams",
        shouldShow: authorizedFor({ roles: ['User'] })
      },
    ],
  };
}
