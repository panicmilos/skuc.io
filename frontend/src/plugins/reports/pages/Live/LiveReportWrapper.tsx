import { FC, useState } from "react";
import { EventsSocketIoClient } from "../../clients";
import PlayArrowIcon from '@mui/icons-material/PlayArrow';
import PauseIcon from '@mui/icons-material/Pause';
import { Link } from "@mui/material";
import CloseIcon from '@mui/icons-material/Close';
import { ChartContainer } from "../../components/containers/ChartContainer";
import { LiveReport } from "../../components";

type Props = {
  paramName: string,
  locationName: string,
  client: EventsSocketIoClient,
  close: () => void,
}

export const LiveReportWrapper: FC<Props> = ({ paramName, locationName, client, close }) => {

  const [paused, setPaused] = useState(false);

  const handleClose = (e: any) => {
    e.preventDefault();
    close();
  }

  const handlePlayPause = (e: any) => {
    e.preventDefault();
    setPaused(!paused);
  }

  const PlayPauseMenu = () =>
    <Link onClick={handlePlayPause} role="button">
      { paused ? <PlayArrowIcon /> : <PauseIcon /> }
    </Link>

  const Buttons = () => <div className="row mr-1">
    <PlayPauseMenu />
    <Link onClick={handleClose} role="button">
      <CloseIcon />
    </Link>
  </div>;
  
  return (
    <ChartContainer title={`Live Data for ${paramName} at ${locationName}`} buttons={<Buttons />}>
      <LiveReport
        paused={paused}
        client={client}
        paramName={paramName}
      />
    </ChartContainer>
  );
}