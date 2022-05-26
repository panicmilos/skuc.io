import { FC, useState } from "react";
import { createUseStyles } from "react-jss";
import { Button, Container, Table, TableBody, TableHead, TableRow, TextInput } from "../../imports";
import { ThresholdConfiguration } from "../../models";

type Props = {
  thresholdConfiguration: ThresholdConfiguration,
  onChange: (tc: ThresholdConfiguration) => any
}

const useStyles = createUseStyles(() => ({
  button: {
    alignSelf: 'center',
    padding: '0.25em',
    flexGrow: 0
  },
  container: 'margin-bottom: 0.5em'
}));

function isNumeric(n: any) {
  return !isNaN(parseFloat(n)) && isFinite(n);
}

export const ThresholdConfigurationForm: FC<Props> = ({ thresholdConfiguration, onChange }) => {

  const [localThresholdConfiguration, setLocalThresholdConfiguration] = useState(thresholdConfiguration);

  const [key, setKey] = useState('');
  const [min, setMin] = useState('0');
  const [max, setMax] = useState('0');
  const [errorMessage, setErrorMessage] = useState('');

  const classes = useStyles();

  const handleAdd = () => {
    const existingThresholds = Object.keys(thresholdConfiguration);
    if (existingThresholds.includes(key)) {
      setErrorMessage('Threshold already exists.');
      return;
    }

    if (!isNumeric(min)) {
      setErrorMessage('Min must be a number.');
      return;
    }

    if (!isNumeric(max)) {
      setErrorMessage('Max must be a number.');
      return;
    }

    localThresholdConfiguration[key] = {
      min: +min,
      max: +max
    };

    setLocalThresholdConfiguration({ ...localThresholdConfiguration });
    onChange({ ...localThresholdConfiguration });

    resetState();
  };

  const resetState = () => {
    setKey('');
    setMin('');
    setMax('');
    setErrorMessage('');
  }

  const removeThreshold = (key: string) => {
    delete localThresholdConfiguration[key];

    setLocalThresholdConfiguration({ ...localThresholdConfiguration });
    onChange({ ...localThresholdConfiguration });
  }
  
  return (
    <>
      <Container className={classes.container}>

        <TextInput label="Key" value={key} onChange={setKey} />
        <TextInput label="Min" value={min} onChange={setMin} />
        <TextInput label="Max" value={max} onChange={setMax} />
        <Button className={classes.button} onClick={handleAdd}>Add</Button>
      </ Container>
      {errorMessage}

      <Table hasPagination={false}>
        <TableHead columns={['Key', 'Min', 'Max', 'Action']}/>
        <TableBody>
        {
          Object.keys(localThresholdConfiguration)?.map((thresholdKey, i) =>
            {
              const thresholdValues = (localThresholdConfiguration as any)[thresholdKey];
              return <TableRow
                        key={i}          
                        cells={[
                          thresholdKey,
                          thresholdValues.min,
                          thresholdValues.max,
                          <Button type="button" onClick={() => removeThreshold(thresholdKey)}>Remove</Button>
                        ]}
                      />
            }
          )
        }
        </TableBody>
      </Table>
    </>
  )
}