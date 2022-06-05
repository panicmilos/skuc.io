import { FeaturePlugin } from "./imports";
import LoginIcon from "@mui/icons-material/Login";
import { Diagram } from "./Diagram";
import { PaddingContainer } from "../sidebar";

export function getPluginDefinition(): FeaturePlugin {
  return {
    id: "LiveDiagram",
    type: "FeaturePlugin",
    menuItems: [
      {
        label: "Diagram",
        path: "/diagrams",
        icon: <LoginIcon />,
      },
    ],
    pages: [
      {
        component: (
          <PaddingContainer>
            <Diagram diagramFilePath="/diagrams/diagram.drawio.xml" groupId="" />
          </PaddingContainer>
        ),
        path: "/diagrams",
      },
    ],
  };
}
