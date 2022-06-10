import { FeaturePlugin } from "./imports";
import { Diagram } from "./Diagram";
import { PaddingContainer } from "../sidebar";
import { authorizedFor } from "../auth-context";

export function getPluginDefinition(): FeaturePlugin {
  return {
    id: "LiveDiagram",
    type: "FeaturePlugin",
    menuItems: [
    ],
    pages: [
      {
        component: (
          <PaddingContainer>
            <Diagram diagramFilePath="/diagrams/diagram.drawio.xml" />
          </PaddingContainer>
        ),
        path: 'groups/:groupId/locations/:locationId/diagrams',
        shouldShow: authorizedFor()
      },
    ],
  };
}
